import tkinter

IMAGE_PATH = "/Users/mykelxknight/CodeProjects/Aktaion/python/researchDev/giphy.gif"

class Splash(object):
      "Splash Screen GUI"
      def __init__(self, root):
          self.root = root
          # No window borders and decoration
          self.root.overrideredirect(True)
          # Get the size of the screen
          screen_width = self.root.winfo_screenwidth()
          screen_height = self.root.winfo_screenheight()
          # Full screen
          geometry_text = "%dx%d+0+0" % (screen_width, screen_height)
          self.root.geometry(geometry_text)
          # Display an image
          self.label = tkinter.Label(self.root)
          # Only GIF and PGM/PPM supported, for more information see:
          self.label._image = tkinter.PhotoImage(file=IMAGE_PATH)
          #http://effbot.org/tkinterbook/photoimage.htm
          self.label.configure(image = self.label._image)
          self.label.pack()
          # This will quit the screen after about 5 seconds
          self.root.after(5000, self.root.quit)

if __name__ == '__main__':
     ROOT = tkinter.Tk()
     APPL = Splash(ROOT)
     ROOT.mainloop()