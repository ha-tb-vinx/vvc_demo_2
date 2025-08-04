@echo off
rem =======================================
rem MSTB101-030 PLU店別商品マスタ作成処理
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MSTB101-030 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL%
