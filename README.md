# YTLoader
## This is a program that can download video files and a video sound from YouTube.
### Stack:
* Java SE 17  
* JavaFx
* ffmpeg
* yt-dlp

There are [_ffmpeg_](https://ffmpeg.org/) and [_yt-dlp_](https://github.com/yt-dlp/yt-dlp) documentation.

### Visualization

<div align="center" >
    <img width="40%" src="https://github.com/itdonut/YouTubeLoader/blob/313abf7be0d2fb1e8d38d1b4e76eb3504fabefc3/screenshots/main.png?raw=true"/>
    <p>Img. 1. Main window.</p>
</div>

When you have opened the program you need to fill in all fields (text input and checkbox) to download what you need. 
Example:

<div align="center" >
    <img width="40%" src="https://github.com/itdonut/YouTubeLoader/blob/313abf7be0d2fb1e8d38d1b4e76eb3504fabefc3/screenshots/main_2.png?raw=true"/>
    <p>Img. 2. Correctly filled fields.</p>
</div>

The program definitely supports the following links:
* ht<span>tp://youtu.be/12345678901
* ht<span>tp://youtube.com/watch?v=12345678901

### Installing and creating an executable file:
1. You need to download [_ffmpeg_](https://ffmpeg.org/download.html) and [_yt-dlp_](https://github.com/yt-dlp/yt-dlp/releases) (exe files) and put them in the src/main/java/com/music/loader/musicloader directory. Or you can choose another directory, but then you need to change paths in the [MainController.java](https://github.com/itdonut/YouTubeLoader/blob/master/src/main/java/com/music/loader/musicloader/conrollers/MainController.java) file in the download method.
2. After this you can make _jar_ file and using [_Launch4j_](https://launch4j.sourceforge.net/) you can create exe file. [Here](https://medium.com/@vinayprabhu19/creating-executable-javafx-application-part-2-c98cfa65801e) you can find how to do it.
3. Now you have executable file and can use the program. Enjoy!

Thank you for your time. The program will be supported.