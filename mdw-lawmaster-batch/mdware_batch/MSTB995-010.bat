@echo off
rem =======================================
rem MSTB995-010 計量器用翌日有効商品マスタワーク作成
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MSTB995-010 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL%
