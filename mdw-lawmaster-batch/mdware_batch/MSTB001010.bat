@echo off
rem =======================================
rem MSTB001010 指定日POS送信開始処理（MSTB001010）
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MSTB001010 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL%
