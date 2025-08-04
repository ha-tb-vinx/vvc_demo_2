@echo off
rem =======================================
rem MSTB992-040 POS用商品マスタエラーワーク作成
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MSTB992-040 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL%
