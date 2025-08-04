@echo off
rem =======================================
rem MBA2-13-01 IF店舗納品不可日マスタ作成
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MBA2-13-01 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL% 
