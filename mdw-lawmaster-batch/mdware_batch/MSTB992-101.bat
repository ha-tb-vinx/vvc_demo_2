@echo off
rem =======================================
rem MSTB992-101 POS�p�e�[�u���o�b�N�A�b�v
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MSTB992-101 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL%
