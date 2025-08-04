@echo off
rem =======================================
rem MSTB994-021 指定日POS用IFカテゴリマスタ作成
rem =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MSTB994-021 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL%
