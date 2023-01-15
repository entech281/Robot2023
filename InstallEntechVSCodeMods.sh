#!/bin/bash

INSTALL_DIR=${HOME}/wpilib/2023/vscode/VSCode-linux-x64/data/extensions/wpilibsuite.vscode-wpilib-2023.1.1/resources/java/src/commands

if [ ! -d "${INSTALL_DIR}" ]
then
    echo "Install directory not found -- Either install WPILib for this user or fix the script for updated version of WPILib"
    echo "Install tried here: '${INSTALL_DIR}'"
    exit 1
fi

cp -r vscode-resources/* ${INSTALL_DIR}
