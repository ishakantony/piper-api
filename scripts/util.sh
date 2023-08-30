#!/bin/bash

# To print with colors
print_color() {
    local color=$1
    local message=$2

    # Set color code based on input
    case $color in
        "black") color_code="\033[30m" ;;
        "red") color_code="\033[31m" ;;
        "green") color_code="\033[32m" ;;
        "yellow") color_code="\033[33m" ;;
        "blue") color_code="\033[34m" ;;
        "magenta") color_code="\033[35m" ;;
        "cyan") color_code="\033[36m" ;;
        "white") color_code="\033[37m" ;;
        *) echo "Invalid color" ; return 1 ;;
    esac

    # Print message in color
    echo -e "${color_code}${message}\033[0m"
}
