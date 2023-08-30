# Piper Backend

This backend for Piper (A tool to manage pipelines and jobs).

## Setup

Recommended IDE is Intellij, if you wish to use other IDE, please configure it accordingly.

1. Run `git config core.hooksPath .git-hooks`, this is to set up tooling to make sure you are following the standard practice in the project
2. Read `CONTRIBUTING.md`
3. You can find configuration for local and other environments in `environments` folder
    1. You can use `local.env` for developing things locally, otherwise you can use any other config
    2. You can also use `EnvFile` plugins in Intellij to make it easier to switch environment
4. On every development, you should start with what the API would look like and should be documented in `docs/api.yml`
