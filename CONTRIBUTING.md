# Contributing

Please follow guidelines on how to contribute to this project as per below.

## Commits

### Convention

We follow standards from https://www.conventionalcommits.org/en/v1.0.0/#specification, please get yourself familiar with it. Commits that doesn't follow standard practice will be rejected.

In addition to following conventional commits, we have our own commit structure as per below.

```text
<type>(<github-issue-number): <description>

[optional body]

[optional footer(s)]
```

Example:

```text
feat(#12): set up authentication

- User can login using plain old username and password
- User can login using social platform Instagram, Facebook and Github
- User can reset password
- Access token valid for 15 minutes, refresh token for 7 days

Reviewed-by: @ishakantony
```

Allowed commits `types` as per below.

- build: Changes that affect the build system or external dependencies
  - Adding or removing dependencies from `pom.xml`
  - Upgrading parent pom version
  - Any other changes in `pom.xml` **EXCEPT** updating version number
- ci: Changes to our CI configuration files and scripts
  - Updating checkstyle and other tooling that helps development workflow
- docs: Documentation only changes
  - Add some comments in the code
  - Update OAS
  - Update ERD
  - Update any documentation related files (e.g. README.md, CONTRIBUTING.md, etc.)
- feat: A new feature
  - Build new API
  - Add new logic in the code that wasn't there before
- fix: A bug fix
  - Fix a bug that exist in the code
- perf: A code change that improves performance
- refactor: A code change that neither fixes a bug nor adds a feature
- style: Changes that do not affect the meaning of the code (white-space, formatting, missing semi-colons, etc.)
  - Use this after formatting your code (e.g. using spring-javaformat)
- test: Adding missing tests or correcting existing tests

### Disclaimer

- If you are confused what `type` of commit to use, contact the maintainer of the project
- Should there's more than 1 `types` fit in the commit, separate it into multiple commits
