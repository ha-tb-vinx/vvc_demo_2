@echo off
rem =======================================
rem MB38-0C-05 店別商品例外マスタ生成
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MB38-0C-05 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL% 
