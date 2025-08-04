@echo off
rem =======================================
rem MB38-0C-03 ギフト商品マスタ生成
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MB38-0C-03 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL% 
