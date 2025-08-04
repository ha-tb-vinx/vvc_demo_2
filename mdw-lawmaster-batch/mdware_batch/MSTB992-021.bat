@echo off
rem =======================================
rem MSTB992-021 POS用IFカテゴリマスタ作成
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MSTB992-021 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL%
