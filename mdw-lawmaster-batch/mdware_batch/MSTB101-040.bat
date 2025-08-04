@echo off
rem =======================================
rem MSTB101-040 グループ売変除外品反映処理
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MSTB101-040 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL%
