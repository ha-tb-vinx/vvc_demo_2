@echo off
rem =======================================
rem MBB3-02-01 不稼働商品ワーク作成処理
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MBB3-02-01 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL%
