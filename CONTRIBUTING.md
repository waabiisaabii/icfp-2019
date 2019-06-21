# Contribution Guide

## PreReqs

Make sure you have IntelliJ installed. (Community edition will be fine.)

> You can install intellij for you mac with: `brew cask install intellij-idea-ce`

Install Bazel (a build tool we'll use) with: `brew install bazel`


## Getting Started Steps

- [ ] Create a fork of the [GitHub repo](https://github.com/godaddy-icfp/icfp-2019)
- [ ] Clone your fork
    ```bash
    git clone git@github.com:<your-user>/icfp-2019.git
    ```
- [ ] Add the upstream to your clone
    ```bash
    git remote add upstream  git@github.com:godaddy-icfp/icfp-2019.git
    git fetch upstream
    ```
    > Note: you don't have to call it `upstream`, you can name it whatever you want, just change the bellow commands to whatever you pick.
      I personally use `icfp` for the master remotes name
- [ ] Add or pickup and item off the [work board](https://github.com/godaddy-icfp/icfp-2019/projects/1) and change your status to `coding` on the [status board](https://github.com/godaddy-icfp/icfp-2019/projects/2)
- [ ] Create a branch to do your work in
    ```bash
    git checkout -b my_work
    ```
- [ ] Make your changes and commit them using `git commit`
- [ ] Push your changes
    ```bash
    git push -u origin <branch_name_from_above>
    ```
- [ ] Submit a PR of your changes
    - [ ]  Notify the [slack channel](https://godaddy.slack.com/messages/CJ550ALQ4) of your PR
- [ ] You can update your master to the latest version at any time using
    ```bash
    git checkout master
    git fetch upstream
    git rebase upstream/master
    ```

If you have any issues with the above then you can reference the github docs:
https://help.github.com/en/articles/configuring-a-remote-for-a-fork
https://help.github.com/en/articles/syncing-a-fork
or ask in the [slack channel](https://godaddy.slack.com/messages/CJ550ALQ4)

