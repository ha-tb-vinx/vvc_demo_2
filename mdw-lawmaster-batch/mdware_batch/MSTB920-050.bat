@echo off
rem =======================================
rem MSTB992-050 緊急POS用WK種別マスタ作成
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MSTB920-050 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL%
