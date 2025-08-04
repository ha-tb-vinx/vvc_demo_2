@echo off
rem =======================================
rem MBA1-01-01 バッチ処理用マスタ作成
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MBA1-01-01 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL% 
