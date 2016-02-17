#!/usr/bin/env python3

import requests
import configparser
from PyQt5.QtWidgets import QWidget, QLabel, QApplication, \
        QVBoxLayout, QLineEdit, QPushButton
from PyQt5 import QtCore
import hashlib
import random
import json
import sys
import os
import keylogger
import threading


class Translator(object):
    URL = 'http://api.fanyi.baidu.com/api/trans/vip/translate'
    Lang = ['auto', 'zh', 'en', 'yue', 'wyw', 'jp',
            'kor', 'fra', 'spa', 'th', 'ara', 'ru',
            'pt', 'de', 'it', 'el', 'nl', 'pl',
            'bul', 'est', 'dan', 'fin', 'cs', 'rom',
            'slo', 'swe', 'hu', 'cht']

    def __init__(self, fromLang, toLang):
        self.setfromLang(fromLang)
        self.setToLang(toLang)
        self.__getConfig()

    def __getConfig(self):
        conf = configparser.ConfigParser()
        direction = os.path.realpath(__file__)
        direction = direction.rsplit('/', 1)[0]
        print(direction)
        conf.read(direction + '/appid.conf')
        self.appid = conf['DEFAULT']['appid']
        self.keys = conf['DEFAULT']['keys']

    def setfromLang(self, fromLang):
        if fromLang not in Translator.Lang:
            print("Not define", fromLang, "as source language")
            raise IOError
        self.fromLang = fromLang

    def setToLang(self, toLang):
        if toLang == 'auto' or toLang not in Translator.Lang:
            print("Not define", toLang, "as result language")
            raise IOError
        self.toLang = toLang

    def __encrypt(self, salt, q):
        sign = self.appid + q + salt + self.keys
        m1 = hashlib.md5()
        m1.update(sign.encode("utf-8"))
        return m1.hexdigest()

    def __getJson(self, q):
        salt = str(random.randint(32768, 65536))
        sign = self.__encrypt(salt, q)

        msg = {
                'appid': self.appid,
                'q': q,
                'from': self.fromLang,
                'to': self.toLang,
                'salt': salt,
                'sign': sign
        }
        try:
            response = requests.post(Translator.URL, data=msg)
            content = response.content
            response.close()
            return content
        except Exception as e:
            print(e)

    def translate(self, q):
        results = self.__getJson(q)
        results = json.loads(results.decode('utf8'))
        if 'error_code' in results.keys():
            return 'error_code: ' + results['error_code'] + \
                    '\nerror_msg: ' + results['error_msg']
        resultLang = '\n'.join(
                (result['dst'] for result in results['trans_result'])
        )
        return resultLang


class MyWindow(QWidget):
    trans = Translator("en", "zh")

    def __init__(self):
        super(MyWindow, self).__init__()

        self.closed = False  # for keylogger
        self.setWindowFlags(QtCore.Qt.WindowStaysOnTopHint)
        self.adjustSize()
        self.setWindowTitle("快速翻译工具")

        self.label = QLabel("<center>请输入单词</center>")
        self.label.setWordWrap(True)
        self.label.adjustSize()

        self.edit = QLineEdit()
        self.edit.returnPressed.connect(self.translateByInput)

        self.button = QPushButton("翻译")
        self.button.clicked.connect(self.translateByInput)

        layout = QVBoxLayout(self)
        layout.addWidget(self.label)
        layout.addWidget(self.edit)
        layout.addWidget(self.button)

        t = threading.Thread(target=self.key_detect)
        t.start()

    def closeEvent(self, event):
        self.closed = True
        event.accept()

    def translateByInput(self):
        text = self.edit.text()
        if not text:
            self.label.setText("<center>请输入单词</center>")
            return
        self.translate(text)

    def translateByClipboard(self):
        cmd = "xclip -o"
        text = os.popen(cmd).read().strip()
        if not text:
            self.label.setText("<center>剪切板内无数据</center>")
            return
        self.translate(text)

    def translate(self, text):
        self.label.setText("翻译中。。。")
        self.label.setText(MyWindow.trans.translate(text) + '\n' + text)

    def key_detect(self):
        def __detectKey(t, modifiers, keys):
            if (modifiers['left shift'] or modifiers['right shift']) \
                    and keys == '<esc>':
                self.translateByClipboard()
        keylogger.log(lambda: self.closed, __detectKey)


if __name__ == '__main__':
    app = QApplication(sys.argv)
    window = MyWindow()
    window.show()

    sys.exit(app.exec_())
