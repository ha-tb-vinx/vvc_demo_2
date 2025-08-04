@echo off
rem =======================================
rem MSTB201-030 イニシャルPLU店別商品マスタ作成
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MSTB201-030 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL%
