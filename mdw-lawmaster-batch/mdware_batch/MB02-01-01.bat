@echo off
rem =======================================
rem MB02-01-01 商品分類体系マスタ作成処理
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MB02-01-01 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL% 
