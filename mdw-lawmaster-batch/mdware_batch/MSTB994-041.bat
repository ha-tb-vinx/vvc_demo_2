@echo off
rem =======================================
rem MSTB994-041 指定日POS用商品マスタエラーワーク作成
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MSTB994-041 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL%
