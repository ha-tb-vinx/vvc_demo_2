@echo off
rem =======================================
rem MBA0-02-01 新店用店別商品例外マスタ作成処理
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MBA0-02-01 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL% 
