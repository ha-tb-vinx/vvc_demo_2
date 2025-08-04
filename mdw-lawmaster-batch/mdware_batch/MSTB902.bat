@echo off
REM ============================================================================
REM タイトル：MSTB902 POS売変データ連携処理
REM 説明　　：POS単価変更ログを取り込み、店別POS商品マスタへの反映を行う
REM version 3.00 2013/12/18 O.Uemura:新規
REM ============================================================================

call %~dp0\SetBatchPath.bat

REM ===== POS単価変更ログデータ取込処理
%execjava% mdware.common.batch.util.control.BatchController MSTB902-010 %CLS_HOME%\batch\properties release use
IF %ERRORLEVEL% NEQ 0 goto error

REM ===== 日別店別POS商品マスタ作成処理
%execjava% mdware.common.batch.util.control.BatchController MSTB902-020 %CLS_HOME%\batch\properties release use
IF %ERRORLEVEL% NEQ 0 goto error

REM ===== 店別POS商品マスタ作成処理
%execjava% mdware.common.batch.util.control.BatchController MSTB902-030 %CLS_HOME%\batch\properties release use
IF %ERRORLEVEL% NEQ 0 goto error

:end

exit 0

:error

exit 1
