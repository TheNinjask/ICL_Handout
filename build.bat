@echo off
title Parsing && cls
javacc Parser0.jj && javac *.java && cls && title java Parser && java Parser
