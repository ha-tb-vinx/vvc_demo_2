@echo off
rem =======================================
rem MBA2-24-01 IF商品発注納品基準日マスタ作成
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MBA2-24-01 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL% 
