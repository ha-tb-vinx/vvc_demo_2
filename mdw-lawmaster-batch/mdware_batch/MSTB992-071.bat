@echo off
rem =======================================
rem MSTB992-071 POS�p�t�@�C���쐬
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MSTB992-071 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL%
