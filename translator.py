# -*- encoding:utf-8 -*-
import urllib
import urllib2
import json
import codecs
import re
from Tkinter import *
import socket
import os
import threading

URL = "http://translate.google.cn/translate_a/single?client=t&sl=en&tl=zh-CN&hl=zh-CN&dt=bd&dt=ex&dt=ld&dt=md&dt=qca&dt=rw&dt=rm&dt=ss&dt=t&dt=at&ie=UTF-8&oe=UTF-8&otf=1&ssel=0&tsel=0&kc=5&tk=522736|742654&q="

class Application(Frame):
    def __init__(self, master=None):
        Frame.__init__(self, master)
        self.pack()
        self.s = None
        self.createWidgets()
        self.master.title('translator')
        #self.bind('<<chglbl>>', lambda text: self.changeLabel(text))
        t = threading.Thread(target=self.startServer, name='serverThread')
        t.start()
        #self.after(5, self.startServer)
        self.mainloop()

    def createWidgets(self):
        self.transLabel = Label(self, text='translator label')
        self.transLabel.pack()
        self.quitButton = Button(self, text='Quit', command=self.quit)
        self.quitButton.pack()

    def changeLabel(self, text):
        self.transLabel['text'] = text
    
    def quit(self):
        if self.s != None:
            s1 = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
            s1.connect(('127.0.0.1', 2333))
            s1.send('exit')
            s1.close()
        self.tk.quit()

    def startServer(self):
        self.s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self.s.bind(('127.0.0.1', 2333))
        self.s.listen(1)
        while(True):
            sock, addr = self.s.accept()
            data = sock.recv(20)
            sock.close()
            if data == 'trans':
                text = self.getTextFromXclip()
                if(not text):
                    continue
                trans = self.translate(text)
                self.changeLabel(trans[0][0][0])
            elif data == 'exit':
                self.s.close()
                break

    def getTextFromXclip(self):
        cmd = "xclip -o"
        text = os.popen(cmd)
        return text.read()


    def translate(self, orginalText):
        '''
        translate English to Chinese
        '''
        transUrl = URL + orginalText.replace(' ', '%20')

        headers = {
                'User-Agent': 'Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:40.0) Gecko/20100101 Firefox/40.0',
                'Host': 'translate.google.cn',
                'User-Agent': 'Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:40.0) Gecko/20100101 Firefox/40.0',
                'Accept': 'text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8',
                'Accept-Language': 'zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3',
                #'Accept-Encoding': 'gzip, deflate',
                'Referer': 'http://translate.google.cn/',
                #'Cookie': '_ga=GA1.3.1555123562.1434506244',
                'Connection': 'keep-alive',
        }

        req = urllib2.Request(transUrl, headers = headers)

        result = urllib2.urlopen(req)
        resultJson = result.read()
        result.close()

        # make the 
        resultJson = re.sub(r'(?<=,)\s*,', ' null,', resultJson)
        resultJson = resultJson.replace('[,', '[null ,')
        return json.loads(resultJson, encoding="utf8")

if __name__ == '__main__':
    app = Application()
