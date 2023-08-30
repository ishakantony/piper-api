#!/bin/bash

# Change the working directory to the directory where the script is located
cd "$(dirname "$0")" || exit 1

# Imports
source ./util.sh
source ./commit-message-checker.sh

# Start Script

print_color "yellow" "
=======================================================
Commits Checker

Motto: 'In doubt, contact your maintainer!'
=======================================================
"

if ! command -v git >/dev/null 2>&1 ; then
    print_color "red" "[ERROR] git is not available"
    exit 1
else
    print_color "blue" "[CHECKS] git is available"
fi

default_branch=master
target_branch=sit/2023-02-27

commits_string=$(git log ${default_branch}..${target_branch} --no-merges --pretty=format:"%h %ae %s")

# Create an empty array to store the lines
commits=()

# Loop over the string, splitting it into lines
while IFS= read -r line; do
  if [[ -n "$line" ]]; then
    commits+=("$line")
  else
    commits+=("")
  fi
done <<< "$commits_string"

regex="([0-9a-f]{9})[[:space:]]([^[:space:]]+)[[:space:]](.+)"

errors_count=0

for index in "${!commits[@]}"; do
  commit_msg="${commits[$index]}"
  # Use grep to extract the date and store it in a variable
  if [[ $commit_msg =~ $regex ]]; then
      sha="${BASH_REMATCH[1]}"
      author="${BASH_REMATCH[2]}"
      message="${BASH_REMATCH[3]}"
      print_color "blue" "[COMMIT-DETAILS] SHA: \"$sha\""
      print_color "blue" "[COMMIT-DETAILS] AUTHOR: \"$author\""
      print_color "blue" "[COMMIT-DETAILS] MESSAGE: \"$message\""

      check_commit_message "$message"
      result=$?
      ((errors_count+=result))
  else
      print_color "red" "[ERROR] Invalid commit '$commit_msg'"
  fi
done

if [ "$errors_count" -ne 0 ]; then
  print_color "red" "[ERROR] One or more commits is invalid, please check logs above and rectify the issue(s)."
else
  print_color "yellow" "[SUCCESS] All commits are valid!"
fi
