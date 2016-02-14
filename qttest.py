from PyQt5.QtWidgets import (QApplication, QLabel)  
import sys  
  
  
if __name__ == "__main__":  
    app = QApplication(sys.argv)  
    label = QLabel("<center>Hello World with PyQt5!</center>")  
    label.resize(200, 50)  
    label.show()  
    sys.exit(app.exec_())  
