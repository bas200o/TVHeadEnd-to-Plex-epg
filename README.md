# TVHeadEnd to Plex epg

Small Java based application to convert TVHeadend EPG to Plex XMLTV format
As off right now pictures by default work with only ziggo up to channel 29.

## note
No the code is not clean and the code style was dependent on the time of day. 
This program is in beta and will be until i find the time again to make it perfect.
Feel free to fork it and make you own or create pull request to make mine better.

## channel icons in Plex
Channel icon's are provide via a json file with links to images.
This file has a brance in this repo and is open for editing via issues or pull request with label **AddIcon**.
If you are going to make a issue note that .svg files will not be read by Plex.
I have not found issues with .png .jpg files

## semi known issues
if you know the fix for these things plese make a new issue and i will take a look at it.
```
daylight savings time
other countrys channel icons
```

## How to build
1. Download intellij ide
2. Download and open project
3. Edit CONFIG.config to you liking
4. Make artifacts and run
5. Use windows task planner or eqivilant to run the program once per day
