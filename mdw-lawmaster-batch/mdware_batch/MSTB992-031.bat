@echo off
rem =======================================
rem MSTB992-031 POS�p�J�e�S���}�X�^�o�b�N�A�b�v
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MSTB992-031 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL%
