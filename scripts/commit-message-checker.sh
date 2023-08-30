#!/bin/bash

### START HELPER FUNCTIONS

one_line_commit_error_message="Single line commit must follow the format of:

<type>(#<github_issue_number>): <message>

Example:
- feat(#123): create alert API
- fix(#45): wrong data type response in /api/order/{transactionId}"

commit_subject_regex="^feat\(#\d+\):\s[a-z]{1}.+$"

multi_line_commit_error_message="Multiline commit must follow the format of:

<type>(#<github_issue_number>): <message>
<empty_line>
<body>

Example:
- feat(#123): create alert API

  New api 'GET /api/v1/alert'
  Configure database connection using MariaDB

  Reviewed by: @ishakantony"

commit_subject_regex="^(build|ci|docs|feat|fix|perf|refactor|style|test)\(#[0-9]+\):[[:space:]][a-z]{1}.+$"

commit_body_regex="^[a-zA-Z0-9]+"

function print_error() {
  errors=$1

  # Create an empty array to store the lines
  lines=()

  # Loop over the string, splitting it into lines
  while IFS= read -r line; do
    if [[ -n "$line" ]]; then
      lines+=("$line")
    else
      lines+=("")
    fi
  done <<< "$errors"

  for line in "${lines[@]}"; do
    echo -e "\033[31m[ERROR]\033[0m $line" >&2
  done

}

function validate_commit_subject() {
#  echo -e "\033[34m[CHECKS] Validating commit subject\033[0m"

  commit_msg=$1

  if [[ $commit_msg =~ $commit_subject_regex ]]; then
    return 0
  fi

  print_error "Commit message '$commit_msg' in violation of rule /$commit_subject_regex/"
  return 1
}

function validate_commit_body() {
#  echo -e "\033[34m[CHECKS] Validating commit body\033[0m"

  commit_msg=$1

  if [[ ! $commit_msg =~ $commit_body_regex ]]; then
    print_error "Commit message '$commit_msg' in violation of rule /$commit_body_regex/"
    return 1
  fi

  return 0
}

function validate_multiline_commit() {
  commit_msg=$1

  # Create an empty array to store the lines
  commit_lines=()

  # Loop over the string, splitting it into lines
  while IFS= read -r line; do
    if [[ -n "$line" ]]; then
      commit_lines+=("$line")
    else
      commit_lines+=("")
    fi
  done <<< "$commit_msg"

  error_count=0

  for index in "${!commit_lines[@]}"; do
    commit="${commit_lines[$index]}"
    echo -e "\033[34m[CHECKS] Validating commit line #$index with message '$commit'\033[0m"
    if [ "$index" -eq 0  ]
    then
      validate_commit_subject "$commit"
      ((error_count+$?))
    elif [ "$index" -eq 1 ]; then
      if [ -n "$commit" ]; then
        print_error "There must be an empty line between subject and body in a commit"
        ((error_count++))
      fi
    elif [ "$index" -eq 2 ]; then
      validate_commit_body "$commit"
      ((error_count+=$?))
    else
      if [ -n "$commit" ]; then
        validate_commit_body "$commit"
        ((error_count+=$?))
      fi
    fi
  done

  if [ ! "$error_count" -eq "0" ]; then
    return 1
  fi

  return 0
}

### END HELPER FUNCTIONS

function check_commit_message() {
  # Get commit from git
  commit_msg=$1

  # If multiline commits, need to validate the format is correct, there should be a new line in between the subject and body
  num_of_lines_in_commit_msg=$(echo "$commit_msg" | wc -l)

  if [ "$num_of_lines_in_commit_msg" -eq 1 ]
  then
    echo -e "\033[34m[CHECKS] Validating single line commit\033[0m"
    validate_commit_subject "$commit_msg"
    result=$?
    if [ $result -ne 0 ]; then
      print_error "$one_line_commit_error_message"
    else
      echo -e "\033[33m[SUCCESS] Your commit message is valid!\033[0m"
    fi
  else
    echo -e "\033[34m[CHECKS] Validating multiline commit\033[0m"
    validate_multiline_commit "$commit_msg"
    result=$?
    if [ $result -ne 0 ]; then
      print_error "$multi_line_commit_error_message"
    else
      echo -e "\033[33m[SUCCESS] Your commit message is valid!\033[0m"
    fi
  fi

  return "$result"
}
