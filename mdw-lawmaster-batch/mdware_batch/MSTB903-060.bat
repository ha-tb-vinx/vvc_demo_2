@echo off
rem =======================================
rem MSTB903-060 POP用店別商品例外マスタバックアップ
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MSTB903-060 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL%
