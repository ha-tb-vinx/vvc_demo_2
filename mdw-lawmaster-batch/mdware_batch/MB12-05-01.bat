@echo off
rem =======================================
rem MB12-05-01 マスタ整合成チェック処理
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MB12-05-01 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL% 
