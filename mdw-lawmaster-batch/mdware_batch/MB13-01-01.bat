@echo off
rem =======================================
rem MB13-01-01 商品マスタ一括修正データ登録処理
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MB13-01-01 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL% 
