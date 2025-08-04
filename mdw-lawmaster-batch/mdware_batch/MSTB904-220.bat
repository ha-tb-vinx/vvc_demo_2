@echo off
REM =======================================
REM タイトル：DWH用マスタトリガーファイルバックアップ処理
REM 説明	：マスタ管理のDWH用トリガーファイルをバックアップする
REM version 3.00 2014/02/27 M.Ayukawa:新規作成
REM =======================================
call %~dp0\SetBatchPath.bat
%execjava% mdware.common.batch.util.control.BatchController MSTB904-210 %CLS_HOME%\batch\properties release use
exit %ERRORLEVEL%
