# ICFP 2019
[![Build Status](https://travis-ci.org/godaddy-icfp/icfp-2019.svg?branch=master)](https://travis-ci.org/godaddy-icfp/icfp-2019)

## The Problem
The problem has been announced [here](https://icfpcontest2019.github.io/) 
and you can find the [problem specification here](https://icfpcontest2019.github.io/download/specification-v1.pdf)

## Building

* Install bazel with brew: `brew install bazel`
* Install Bazel plugins in Intellij, restart
* Go to IntelliJ/Preferences, Other Settings/Bazel Settings, and set bazel binary location to `/usr/local/bin/bazel`
* Import the bazel project:
  * from the Welcome To IntelliJ Window, click on Import Bazel Project
  * find the WORKSPACE file in the git repo root, click next
  * select `Create from scratch` for the "project view"
  * change project name to `icfp2019`, click finish


