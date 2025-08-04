@echo off
rem =======================================
rem MSTB904-010 DWH用翌日有効商品マスタワーク作成
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MSTB904-010 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL%
