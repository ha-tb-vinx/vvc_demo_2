@echo off
rem =======================================
rem MSTB992-020 POS用IFカテゴリマスタ作成
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MSTB992-020 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL%
