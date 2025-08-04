@echo off
rem =======================================
rem MB38-0C-01 商品マスタ生成バッチ初期処理
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MB38-0C-01 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL% 
