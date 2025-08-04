@echo off
rem =======================================
rem MSTB992-011 POS用翌日有効カテゴリマスタワーク作成
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MSTB992-011 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL%
