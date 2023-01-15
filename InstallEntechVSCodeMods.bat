SET INSTALL_DIR="C:\Users\Public\wpilib\2023\vscode\data\extensions\wpilibsuite.vscode-wpilib-2023.1.1\resources\java\src\commands"

IF NOT EXIST %INSTALL_DIR% (
    ECHO Install directory not found -- Either install WPILib for this user or fix the script for updated version of WPILib
    ECHO Install to %INSTALL_DIR%
    GOTO :Exit
)


XCOPY vscode-resources/* %INSTALL_DIR% /S /R /Y

:PauseExit
PAUSE
exit
