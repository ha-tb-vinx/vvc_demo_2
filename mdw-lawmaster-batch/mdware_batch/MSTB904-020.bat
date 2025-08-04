@echo off
rem =======================================
rem MSTB904-020 DWH用翌日有効商品マスタエラーワーク作成
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MSTB904-020 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL%
