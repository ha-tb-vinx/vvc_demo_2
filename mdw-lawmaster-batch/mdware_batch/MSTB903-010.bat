@echo off
rem =======================================
rem MSTB903-010 POP用翌日有効商品マスタワーク作成
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MSTB903-010 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL%
