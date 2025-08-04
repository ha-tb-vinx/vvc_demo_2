REM @echo off
REM ============================================================================
REM タイトル	：時間帯データ取込処理
REM 説明	：「POSジャーナル（時間帯）」ファイルを読み込み、レコード内容が時間帯FORMAT1、または時間帯FORMAT2のいずれであるかの判定を行い、
REM 		  それぞれのデータを「時間帯データ（FORMAT1）」テーブルまたは「時間帯データ（FORMAT2）」テーブルへ挿入する。
REM version 1.00 2016/05/20 Hirata:新規
REM ============================================================================

SET BATCHF_HOME=@filter.shellscript.baseDirectory.value@

CALL %BATCHF_HOME%\batch\SetBatchPath.bat

REM ===== 時間帯データ取込処理
%EXECJAVA% jp.co.vinculumjapan.swc.commons.dao.BatchController URIB013410
IF %ERRORLEVEL% NEQ 0 goto error

:end

CD %BATCHF_HOME%

exit 0

:error

exit 1
