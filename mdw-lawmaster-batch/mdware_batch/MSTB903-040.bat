@echo off
rem =======================================
rem MSTB903-040 POP用翌日有効店別商品例外マスタワーク作成
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MSTB903-040 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL%
