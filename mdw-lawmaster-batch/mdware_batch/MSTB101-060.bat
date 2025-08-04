@echo off
rem =======================================
rem MSTB101-060 ハンパー構成反映処理
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MSTB101-060 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL%
