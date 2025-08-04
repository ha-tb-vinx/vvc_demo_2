@echo off
rem =======================================
rem MSTB903-050 POP用IF店別商品例外マスタ作成
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MSTB903-050 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL%
