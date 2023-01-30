# RARBG-subtitles
Automate subtitle renaming after downloading movie or TV show from RARBG

# Introduction

This program is designed to automate the process of renaming subtitle files after downloading movies or TV shows from RARBG. It allows the user to set the priority of languages for subtitles by drag and dropping to right scrollbar, with the highest priority language appearing first on the list.
Requirements

    Java 17 or later
    qBittorrent

# Installation

    Download the program from GitHub releases
    Run the program with the command java -jar <jar_file_name>.jar
    Set up autorun in qBittorrent by going to Tools -> Downloads -> Run external program -> Run on torrent download -> java -jar "full_path_to_jar.jar" "%R"
    Keep quotation marks! - Example: java -jar "C:\Program Files\qBittorrent\qbitsubs.jar" "%R"

# Usage

    1. Open the program and select the languages for subtitles in order of priority.
    2. Download a movie or TV show using qBittorrent.
    3. The program will automatically rename and copy the appropriate subtitle files for the media player to detect. You can now forget about it.

# Note

    Make sure to set the autorun command in qBittorrent correctly, with the correct path to the jar file.
    The program is only tested on Windows 10 OS

# Contribution

    Fork this repository
    Create your feature branch (git checkout -b feature/amazing-feature)
    Commit your changes (git commit -m 'Add some amazing feature')
    Push to the branch (git push origin feature/amazing-feature)
    Create a new Pull Request