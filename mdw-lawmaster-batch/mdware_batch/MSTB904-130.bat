@echo off
rem =======================================
rem MSTB904-130 DWH用翌日有効カテゴリマスタワーク作成
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MSTB904-130 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL%
