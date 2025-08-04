@echo off
rem =======================================
rem MB00-01-01 オンラインフラグ更新処理
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MB00-01-01 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL% 
