@echo off
rem =======================================
rem MB44-01-01 店グルーピングマスタ洗替処理
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MB44-01-01 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL% 
