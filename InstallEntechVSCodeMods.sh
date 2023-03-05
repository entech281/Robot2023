#!/bin/bash

VERSION=2023.4.2
INSTALL_DIR=${HOME}/wpilib/2023/vscode/VSCode-linux-x64/data/extensions/wpilibsuite.vscode-wpilib-${VERSION}/resources/java/src/commands

if [ ! -d "${INSTALL_DIR}" ]
then
    echo "Install directory not found -- Either install WPILib for this user or fix the script for updated version of WPILib"
    echo "Install tried here: '${INSTALL_DIR}'"
    exit 1
fi

cp -r vscode-resources/* ${INSTALL_DIR}
